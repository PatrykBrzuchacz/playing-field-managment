package engineersthesis.playingfieldmanagment.modules.playingField.rating;

import engineersthesis.playingfieldmanagment.modules.playingField.PlayingField;
import engineersthesis.playingfieldmanagment.modules.playingField.PlayingFieldRepository;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.web.dto.RateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private PlayingFieldRepository playingFieldRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Double addVote(Long id, Integer rate) {
        User loggedUser = securityUserHelper.getLoggedUser();
        if(!rateRepository.existsByUserAndPlayingField_Id(loggedUser, id)) {
            PlayingField playingField = playingFieldRepository.getOne(id);

            rateRepository.save(new Rate(playingField,loggedUser,rate));
            Integer numberOfVotes = playingField.getNumberOfVotes();
            if(numberOfVotes==null) {
                numberOfVotes=0;
            }
            if(rate==1 || rate==2 ||rate==3 ||rate==4 ||rate==5) {
                double newRating;
                if(playingField.getRating()!=null) {
                    newRating =
                            Math.round(((playingField.getRating() * numberOfVotes + rate) / (numberOfVotes + 1)) * 100);
                    newRating = newRating / 100;
                } else {
                    newRating = rate;
                }
            playingField.setRating(newRating);
            playingField.setNumberOfVotes(numberOfVotes+1);
            return newRating;
            } else throw new RuntimeException("Rate must be in 1-5");

        } else throw new RuntimeException("You add vote to this playing field before");
    }

    public RateDto getRate(Long id) {
        PlayingField playingField = playingFieldRepository.getOne(id);
        return new RateDto(playingField.getRating(), playingField.getNumberOfVotes());
    }


    public Boolean existsRate(Long id) {
        return rateRepository.existsByUserAndPlayingField_Id(securityUserHelper.getLoggedUser(),id);
    }
}
