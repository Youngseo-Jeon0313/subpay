from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from config.param_generators import get_subscription_status_params
from config.common import default_args, DOCKER_CMD_TEMPLATE

with DAG(
    'subscription_status_batch',
    default_args=default_args,
    description='매일 오전 00시에 구독 상태 업데이트',
    schedule_interval='0 0 * * *',
    catchup=False,
    tags=['subpay'],
    params={
        'check_time': None,   # YYYY-MM-DD
        'dry_run': 'false',
    }
) as dag:

    prepare_status_params = PythonOperator(
        task_id='prepare_status_params',
        python_callable=get_subscription_status_params,
        provide_context=True,
    )

    run_subscription_status = BashOperator(
        task_id='run_subscription_status_job',
        bash_command=DOCKER_CMD_TEMPLATE.format(
            job_name='subscriptionStatusJob',
            param_task_id='prepare_status_params'
        ),
    )

    prepare_status_params >> run_subscription_status
