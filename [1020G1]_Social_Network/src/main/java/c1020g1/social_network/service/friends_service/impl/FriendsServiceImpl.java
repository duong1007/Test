package c1020g1.social_network.service.friends_service.impl;

import c1020g1.social_network.model.Friends;
import c1020g1.social_network.model.SuggestFriend;
import c1020g1.social_network.model.User;
import c1020g1.social_network.repository.UserRepository;
import c1020g1.social_network.repository.friends_repository.FriendsRepository;
import c1020g1.social_network.service.friends_service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Friends> findAllFriendById(Integer id, Pageable pageable) {
        return friendsRepository.findAllFriendById(id, pageable);
    }

    @Override
    public List<SuggestFriend> getAllSuggestFriend(Integer id) {
        List<SuggestFriend> suggestFriendList = new ArrayList<>();
        List<Integer> idUser = friendsRepository.getAllSuggestFriend(id);
        for (int i = 0; i< idUser.size(); i++){
            SuggestFriend suggestFriend = new SuggestFriend();
            suggestFriend.setSuggestFriend(userRepository.getUserById(idUser.get(i)));

            List<User> mutualFriends = friendsRepository.findMutualFriend(id,idUser.get(i));
            suggestFriend.setMutualFriends(mutualFriends);

            suggestFriendList.add(suggestFriend);
        }
        return suggestFriendList;
    }

    @Override
    public Friends findFriendsById(Integer id) {
        return friendsRepository.findFriendsById(id);
    }

    @Override
    public void deleteFriends(Friends friends) {
        friendsRepository.deleteFriendsByUserUserIdAndFriendUserId(friends.getUser().getUserId(), friends.getFriend().getUserId());
        friendsRepository.deleteFriendsByUserUserIdAndFriendUserId(friends.getFriend().getUserId(), friends.getUser().getUserId());

    }

    @Override
    public List<User> findMutualFriend(Integer receiveUser, Integer sendUser) {
        return null;
    }

//    @Override
//    public String addNewFriend(Friends friends, Pageable pageable) {
//        //Check list friend of user has this friend or not.
//        Page<Friends> friendsList = friendsRepository.findAllFriendById(friends.getUser().getUserId(), pageable);
//        for (Friends friends1 : friendsList){
//            if (friends1.getFriend().getUserId() == friends.getFriend().getUserId())
//                return "NG";
//        }
//        friendsRepository.save(friends);
//        return "OK";
//    }
//
//    @Override
//    public List<User> findMutualFriend(Integer receiveUser, Integer sendUser) {
//        return friendsRepository.findMutualFriend(receiveUser,sendUser);
//    }
}
