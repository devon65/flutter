from __future__ import print_function # Python 2/3 compatibility
import boto3
import json
import decimal
from boto3.dynamodb.conditions import Key, Attr
from botocore.exceptions import ClientError
dynamodb = boto3.resource('dynamodb', region_name='us-west-2', endpoint_url="http://localhost:8000")

table = dynamodb.Table('Followers')


def lambda_handler(event, context):
    # TODO implement
    
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }


# def create(userId, userName, userProfileUrl, followerId, followerName, followerProfileUrl):
#     response = table.put_item(
#         Item={
#             'userId': userId,
#             'userName': userName,
#             'userProfileUrl': userProfileUrl,
#             'followerId': followerId,
#             'followerName': followerName,
#             'followerProfileUrl': followerProfileUrl,
#         }
#     )
#     return response

def create(user, follower):
    response = table.put_item(
        Item={
            'userId': user['userId'],
            'followerId': follower['followerId'],
            'user': user,
            'follower': follower,
        }
    )
    return response


def readFollowers(userId):
    try:
        response = table.get_item(
            Key={
                'userId': userId
            }
        )
    except ClientError as e:
        print(e.response['Error']['Message'])
        return None
    else:
        item = response['Item']
        print("GetItem succeeded:")
        return item


def readUsersFollowed(userId):
    try:
        response = table.get_item(
            Key={
                'userId': userId
            }
        )
    except ClientError as e:
        print(e.response['Error']['Message'])
        return None
    else:
        item = response['Item']
        print("GetItem succeeded:")
        return item



print(readFollowers(userId=12))