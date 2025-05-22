from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from config.param_generators import get_subscription_pay_params
from config.common import default_args, DOCKER_CMD_TEMPLATE

with DAG(
    'subscription_pay_batch',
    default_args=default_args,
    description='매일 오전 09시에 구독 결제를 처리합니다',
    schedule_interval='0 9 * * *',
    catchup=False,
    tags=['subpay'],
    params={
        'target_date': None,  # YYYY-MM-DD
        'dry_run': 'false',
    }
) as dag:

    prepare_pay_params = PythonOperator(
        task_id='prepare_pay_params',
        python_callable=get_subscription_pay_params,
        provide_context=True,
    )

    run_subscription_pay = BashOperator(
        task_id='run_subscription_pay_job',
        bash_command=DOCKER_CMD_TEMPLATE.format(
            job_name='subscriptionPayJob',
            param_task_id='prepare_pay_params'
        ),
    )

    prepare_pay_params >> run_subscription_pay
