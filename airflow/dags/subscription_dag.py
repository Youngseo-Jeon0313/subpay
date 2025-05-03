from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2023, 1, 1),
    'retries': 1,
}

dag = DAG('spring_batch_example', default_args=default_args, schedule_interval='@daily')

run_spring_batch = BashOperator(
    task_id='run_spring_batch',
    bash_command='java -jar /path/to/spring-batch-job.jar',
    dag=dag,
)