"""
Airflow DAG 공통 설정
"""

from datetime import datetime, timedelta
import pendulum

# 한국 시간대 설정
kst = pendulum.timezone("Asia/Seoul")

# DAG 기본 인자
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2024, 1, 1, tzinfo=kst),
    'email': ['20wjsdudtj@gmail.com'],
    'email_on_failure': True,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

# Docker 명령어 템플릿
DOCKER_CMD_TEMPLATE = '''
set -x &&
docker run --rm \
    --network subpay_airflow_network \
    -e SPRING_PROFILES_ACTIVE=batch \
    -e SPRING_DATASOURCE_URL=jdbc:mysql://subpay-db:3306/subpay \
    -e SPRING_DATASOURCE_USERNAME=root \
    -e SPRING_DATASOURCE_PASSWORD=subpay \
    -e SPRING_BATCH_JOB_ENABLED=false \
    -e SENTRY_DSN= \
    -e SENTRY_ENABLED=false \
    subpay-batch:latest \
    java -jar /app.jar \
    --spring.batch.job.name={job_name} \
    --{{{{ task_instance.xcom_pull(task_ids="{param_task_id}", key="return_value") }}}}
'''.strip()
