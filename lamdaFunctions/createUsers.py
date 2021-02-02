#mapping template
# #set($path = $input.params().path)
# #set($qs = $input.params().querystring)
# {
#     "params": {
#         #foreach($key in $path.keySet())
#             "$key": "$path.get($key)"
#         #if($foreach.hasNext), #end
#         #end
#     },
#     "query": {
#         #foreach($key in $qs.keySet())
#             "$key": "$qs.get($key)"
#         #if($foreach.hasNext), #end
#         #end
#     },
#     "body": $input.json('$')
# }

def lambda_handler(event, context):
    # TODO implement
    users = make_fake_users(event['pagesize'])
    return {
        'statusCode': 200,
        'body': users
    }

def make_fake_users(pagesize):
    num_users = int(pagesize)
    result = []
    for i in range (num_users//4):
        result.append({"userId":"1", "name":"Charlie Brown", "alias":"@chuck", "profilePic":""})
        result.append({"userId":"2", "name":"Susy Brown", "alias":"@sue", "profilePic":""})
        result.append({"userId":"3", "name":"Peppermint Patty", "alias":"@pepper", "profilePic":""})
        result.append({"userId":"4", "name":"Linus van Pelt", "alias":"@linus", "profilePic":""})

    return result


event = {'pagesize':'20'}
context = 1

print(lambda_handler(event, context))