
u0 = {"userId": "0", "name": "Joe Cool", "alias": "@jcool", "profilePic": ""}
u1 = {"userId": "1", "name": "Charlie Brown", "alias": "@chuck", "profilePic": ""}
u2 = {"userId": "2", "name": "Susy Brown", "alias": "@sue", "profilePic": ""}
u3 = {"userId": "3", "name": "Peppermint Patty", "alias": "@pepper", "profilePic": ""}
u4 = {"userId": "4", "name": "Linus van Pelt", "alias": "@linus", "profilePic": ""}
users = [u0, u1, u2, u3, u4]

h1 = "#rockin"
h2 = "#superb"
h3 = "#studentLife"
h4 = "#dreamBig"
h5 = "#yaass"

global_timestamp = 1521462189
hour = 3600

def lambda_handler(event, context):
    # TODO implement
    nextIndex = int(event['nextindex'])
    global global_timestamp
    global_timestamp += nextIndex * hour

    hashtag = event['hashtag']
    statuses = make_fake_statuses(event['pagesize'], hashtag)
    return {
        'statusCode': 200,
        'body': statuses
    }


def make_fake_statuses(pagesize, hashtag):
    result = []
    for user in users:
        result.extend(createUserStatuses(user, hashtag))
    return result[:20]


def createUserStatuses(user, hashtag):
    result = []
    global global_timestamp
    result.append({"user":user,
                   "statusId": user["userId"] + "1",
                   "messageBody": "This will probably be the best picture you've seen in years! " + hashtag + " " + h1,
                   "attachmentUrl": "https://outdoorgearlab-mvnab3pwrvp3t0.stackpathdns.com/photos/15/81/279616_31645_XXL.jpg",
                   "timeStamp": global_timestamp})
    global_timestamp += hour
    result.append({"user": user,
                   "statusId": user["userId"] + "1",
                   "messageBody": "I like making dinner with " + u1["alias"] + ". Super fun! " + hashtag + " " + h2,
                   "timeStamp": global_timestamp})
    global_timestamp += hour
    result.append({"user": user,
                   "statusId": user["userId"] + "1",
                   "messageBody": "Rock climbing rocks my socks off! #bestWayToGetHigh #liveLifeToTheFullest " + hashtag + " " + h3,
                   "timeStamp": global_timestamp})
    global_timestamp += hour
    result.append({"user": user,
                   "statusId": user["userId"] + "1",
                   "messageBody": "Example.com is a simple, small webpage " + hashtag + " " + h4,
                   "attachmentUrl": "http://www.example.com/",
                   "timeStamp": global_timestamp})
    global_timestamp += hour
    result.append({"user": user,
                   "statusId": user["userId"] + "1",
                   "messageBody": "Loving life to the max! #liveLifeToTheFullest #bestWayToGetHigh " + hashtag + " " + h5,
                   "timeStamp": global_timestamp})
    global_timestamp += hour
    return result


event = {'pagesize':'20', 'nextindex':'0', 'hashtag':'#itsTheSweetness'}
context = 1

print(lambda_handler(event, context))