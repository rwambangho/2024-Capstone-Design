package Capstone.Capstone.servicelmpl;

import Capstone.Capstone.dto.UserDto;
import Capstone.Capstone.entity.User;
import Capstone.Capstone.utils.SmsUtil;
import Capstone.Capstone.repository.UserRepository;
import Capstone.Capstone.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SmsUtil smsUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,SmsUtil smsUtil) {
        this.userRepository = userRepository;
        this.smsUtil=smsUtil;
    }

    @Override
    public void saveUser(User user) {
        // 저장 전에 중복 이메일 체크 등 필요한 로직 수행 가능
        userRepository.save(user);

    }

    @Override
    public User getUserById(String Id) {
        Optional<User> optionalUser = userRepository.findById(Id);
        return optionalUser.orElse(null);
    }

    @Override
    public void updateUserPassword(String Id, String newPassword) {

        Optional<User> optionalUser = userRepository.findById(Id);

        optionalUser.ifPresent(existingUser -> {
            log.info("userId={} pw={}",Id,newPassword);
            existingUser.setPassword(newPassword);
            userRepository.save(existingUser);
        });
    }

    @Override
    public boolean authenticateUser(String Id, String password) {
        Optional<User> optionalUser = userRepository.findById(Id);
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password);
    }


    @Override
    public String sendSms(User user) {


            //수신번호 형태에 맞춰 "-"을 ""로 변환
            String phoneNum = user.getPhoneNumber().replaceAll("-","");



            String verificationCode = smsUtil.generateStoreVerificationCode(phoneNum);
            smsUtil.sendOne(phoneNum, verificationCode);


            return "SMS 전송 성공";
        }

        @Override
        public boolean checkVerificationCode(String phoneNum,String verificationCode){
            phoneNum=phoneNum.replaceAll("-","");

           return smsUtil.checkVerificationCode(phoneNum,verificationCode);

        }
    @Override
    public void switchToDriverMode(User user) {
        if (user.getDriverLicense() == null || user.getDriverLicense().isEmpty()) {
            throw new IllegalStateException("운전면허증을 등록해야 운전자 모드를 사용할 수 있습니다.");
        }
        user.setIsDriver(true);
        userRepository.save(user);
    }

    @Override
    public void registerDriverLicense(User user, String driverLicense) { //면허증 등록
        user.setDriverLicense(driverLicense);
        userRepository.save(user);
    }

    @Override
    public void checkOutUser() {


    }
@Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setNickname(userDto.getNickname());
        return user;
    }
@Override
    public UserDto convertToDto(User user){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setNickname(user.getNickname());
        return userDto;
}




}

