package com.example.flutter.Utils

import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.models.UserAlias

object DummyData {

    const val statusesPerUser = 4
    private val names = arrayListOf("John", "James", "Peter", "Jack", "Fred")
    private val funHashtags = arrayListOf("#Rocking", "#SmokinHot!", "#ILived",
        "#StudentLife", "#GirlsLikeCarsAndMoney", "#ICanGoTheDistance", "#ThereIsBeautyAllAround",
        "#Nature", "#Sweetness", "#BestWayToGetHigh", "#buddingartist")

    private var mfakeUsers: ArrayList<User>? = null
    val fakeUsers: ArrayList<User>
        get() {
            return mfakeUsers ?: makeFakeUsers(Constants.NUM_FAKE_USERS)
        }

    private var mFakeStatuses: ArrayList<Status>? = null
    val fakeStatuses: ArrayList<Status>
        get() {
            return mFakeStatuses ?: makeFakeStatuses()
        }

    private var mSuperUser: User? = null
    val superUser: User
        get() {
            return mSuperUser ?: makeSuperUser()
        }

    private var mIdToStatus: Map<String, Status>? = null
    val idToStatusMap: Map<String, Status>
        get() {
            return mIdToStatus ?: makeIdToStatusMap()
        }

    private var mHashtagToStatuses: Map<String, ArrayList<String>>? = null
    val hashtagToStatuses: Map<String, ArrayList<String>>
        get() {
            return mHashtagToStatuses ?: makeHashtagToStatusesMap()
        }

    private var mIdToUser: HashMap<String, User>? = null
    val idToUserMap: Map<String, User>
        get() {
            return mIdToUser ?: makeIdToUserMap()
        }

    private var mAliasToUser: HashMap<String, User>? = null
    val aliasToUserMap: Map<String, User>
        get() {
            return mAliasToUser ?: makeAliasToUserMap()
        }

    fun getStatusesByHashtag(tag: String): List<Status>{
        val statusList = ArrayList<Status>()
        for (statusId in hashtagToStatuses[tag] ?: listOf<String>()){
            val taggedStatus = idToStatusMap[statusId]
            if (taggedStatus != null) {
                statusList.add(taggedStatus)
            }
        }
        return statusList
    }

    private fun makeFakeUsers(numUsers: Int): ArrayList<User>{
        val users = ArrayList<User>()
        for (id in 1..numUsers){
            val name = getName()
            val alias = Constants.CALLOUT + name.replace(" ", "_") + id
            users.add(User(id.toString(), name, alias))
        }
        mfakeUsers = users
        return users
    }

    private fun makeFakeStatuses(): ArrayList<Status>{
        val users = fakeUsers
        val statuses = ArrayList<Status>()
        for (user in users){
            statuses.addAll(makeUserStatuses(user))
        }
        mFakeStatuses = statuses
        return statuses
    }

    private fun makeUserStatuses(user: User): List<Status>{
        val userStatuses = ArrayList<Status>()
        val dinnerStatus = makeDinnerStatus(user)
        val climbingStatus = makeRockClimbingStatus(user)
        val picStatus = makePictureStatus(user)
        user.statusList.add(dinnerStatus.statusId)
        user.statusList.add(climbingStatus.statusId)
        user.statusList.add(picStatus.statusId)
        userStatuses.add(dinnerStatus)
        userStatuses.add(climbingStatus)
        userStatuses.add(picStatus)
        return userStatuses
    }

    private fun makeDinnerStatus(user: User): Status{
        val messageBody = "I like making dinner with " + fakeUsers.random().alias + ". Super fun!"  +
                " " + funHashtags.random() + " " + funHashtags.random() + " " + funHashtags.random()
        return Status(user, messageBody)
    }

    private fun makeRockClimbingStatus(user: User): Status{
        val messageBody = "Rock climbing rocks my socks off!" + " " + funHashtags.random() +
                " " + funHashtags.random() + " " + funHashtags.random() + " " + funHashtags.random()
        return Status(user, messageBody)
    }

    private fun makePictureStatus(user: User): Status{
        val messageBody = "This will probably be the best picture you've seen in years! " +
                fakeUsers.random().alias  + " " + funHashtags.random() + " " + funHashtags.random()
        return Status(user, messageBody)
    }

    fun getName(): String{
       return names.random() + " " + names.random() + "son"
    }

    private fun makeIdToStatusMap(): Map<String, Status>{
        val idToStatusMap = HashMap<String, Status>()
        for(status in fakeStatuses){
            idToStatusMap[status.statusId] = status
        }
        mIdToStatus = idToStatusMap
        return idToStatusMap
    }

    private fun makeIdToUserMap(): Map<String, User>{
        val idToUserMap = HashMap<String, User>()
        for(user in fakeUsers){
            idToUserMap[user.userId] = user
        }
        mIdToUser = idToUserMap
        return idToUserMap
    }

    fun getUserFeed(user: User?): List<Status>{
        val friendList = user?.usersFollowed
        val statusesOfFriends = ArrayList<Status>()
        for(userId in friendList ?: listOf()){
            statusesOfFriends.addAll(getUserStory(idToUserMap[userId]))
        }
        return statusesOfFriends
    }

    fun getUserStory(user: User?): List<Status>{
        val storyStatuses = ArrayList<Status>()
        for(statusId in user?.statusList ?: listOf<String>()){
            val status = idToStatusMap[statusId]
            if(status != null) { storyStatuses.add(status) }
        }
        return storyStatuses
    }

    private fun makeSuperUser(): User{
        val newSuperUser = User(userId = "0", name = "Joe Cool", alias = "@JCool",
            usersFollowed = idToUserMap.keys.toList(), followers = idToUserMap.keys.toList()
        )
        fakeStatuses.addAll(makeUserStatuses(newSuperUser))
        mIdToUser?.put(newSuperUser.userId, newSuperUser)
        mSuperUser = newSuperUser
        return newSuperUser
    }

    private fun makeAliasToUserMap():  Map<String, User>{
        val newAliasToUserMap = HashMap<String, User>()
        for (user in fakeUsers){
            newAliasToUserMap[user.alias] = user
        }
        mAliasToUser = newAliasToUserMap
        return newAliasToUserMap
    }

    private fun makeHashtagToStatusesMap(): Map<String, ArrayList<String>>{
        val newHashtagToStatusMap = HashMap<String, ArrayList<String>>()

        for (status in fakeStatuses){
            for (tag in status.hashtagList){
                if (newHashtagToStatusMap[tag] == null) {
                    newHashtagToStatusMap[tag] = ArrayList(listOf(status.statusId))
                }
                else{
                    newHashtagToStatusMap[tag]?.add(status.statusId)
                }
            }
        }
        mHashtagToStatuses = newHashtagToStatusMap
        return newHashtagToStatusMap
    }
}