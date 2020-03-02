import requests

URL = "http://localhost:8080"

def createUser(name, username, password, email, handle):
    
    resp = requests.post("{}/user/createUser".format(URL), data = {
        'name': name,
        'username': username,
        'password': password,
        'email': email,
        'handle': handle
    })

    print(resp.text)

def getUserId(username, password):

    resp = requests.get("{}/user/getUserId?username={}&password={}".format(URL, username, password))
    return resp.text

def createBankAccount(userId, routingNumber, balance):
    resp = requests.post("{}/bank/createBankAccount".format(URL), data = {
        "userId": userId,
        "routingNumber": routingNumber,
        "balance": balance
    })

def editProfile(id, newName, newUsername, newPassword, newEmail, newHandle):
    resp = requests.get("{}/user/editProfile?id={}&newUsername={}&newPassword={}&newEmail={}&newHandle={}".format(URL, id, newName, newUsername, newPassword, newEmail, newHandle))
    print(resp.text)

def sendOutgoingFriendRequest(id, friendId):
    resp = requests.post("{}/user/sendOutgoingFriendRequest".format(URL), data = {
        "id": id,
        "friendId": friendId
        })
    print(resp.text)

def cancelFriendRequest(id, friendId):
    resp = requests.get("{}/user/cancelFriendRequest?id={}&friendId={}".format(URL, id, friendId))
    print(resp.text)

def declineFriendRequest(id, friendId):
    resp = requests.get("{}/user/declineFriendRequest?id={}&friendId={}".format(URL, id, friendId))
    print(resp.text)

def getBankIds(username, password):
    resp = requests.get("{}/user/getBankIds?username={}&password={}".format(URL, username, password))
    print(resp.text)
    return ", ".split(resp.text)

def getBalance(bankAccountId):
    resp = requests.get("{}/bank/getBalance?bankAccountId={}".format(URL, bankAccountId))

    print(resp.text)

testname = "bubsy"
name = "{}_name".format(testname)
username = "{}_user".format(testname)
password = "{}_password".format(testname)
email = "{0}@{0}.org".format(testname)
handle = "{}_handle".format(testname)

# Create the user for the first time
createUser(name, username, password, email, handle)

# Get the user's ID
userId = getUserId(username, password)
print("userId: {}".format(userId))

# Show that it will fail if user is taken
createUser(name, username, password, email, handle)

# Create second user account

testname = "shyam"
name2 = "{}_name".format(testname)
username2 = "{}_user".format(testname)
password2 = "{}_password".format(testname)
email2 = "{0}@{0}.org".format(testname)
handle2 = "{}_handle".format(testname)

createUser(name2, username2, password2, email2, handle2)
userId2 = getUserId(username2, password2)
cancelFriendRequest(userId, userId2)

sendOutgoingFriendRequest(userId, userId2)
sendOutgoingFriendRequest(userId, userId2)
cancelFriendRequest(userId, userId2)
cancelFriendRequest(userId, userId2)

sendOutgoingFriendRequest(userId2, userId)
declineFriendRequest(userId, userId2)
declineFriendRequest(userId, userId2)
# Create user bank account

routingNumber = "444444444"
balance = "44444"

createBankAccount(userId, routingNumber, balance)

# get bankId so we can check info about it

user1_bank_ids = getBankIds(username, password)

for bank_id in user1_bank_ids:
    print("Checking balance for bank ID {}".format(bank_id))
    getBalance(bank_id)


