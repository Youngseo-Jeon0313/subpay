"""Airflow DAG 파라미터 생성 모듈"""

from datetime import datetime
import pendulum

kst = pendulum.timezone("Asia/Seoul")

def get_subscription_pay_params(**context):
    """구독 결제 배치 잡 파라미터 """
    execution_date = context.get('params', {}).get('execution_date', datetime.now(kst))
    target_date = execution_date.strftime('%Y-%m-%d')
    dry_run = context.get('params', {}).get('dry_run', 'false')

    return f'target_date={target_date} dry_run={dry_run}'

def get_subscription_status_params(**context):
    """구독 상태 업데이트 배치 작업 잡 파라미터"""
    execution_date = context.get('params', {}).get('execution_date', datetime.now(kst))
    check_time = execution_date.strftime('%Y-%m-%d')
    dry_run = context.get('params', {}).get('dry_run', 'false')

    return f'check_time={check_time} dry_run={dry_run}'
