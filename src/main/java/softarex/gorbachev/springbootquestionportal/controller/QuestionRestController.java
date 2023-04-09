package softarex.gorbachev.springbootquestionportal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.QuestionRequestMappingConst.QUESTIONS_CONTROLLER;

@RestController
@RequestMapping(QUESTIONS_CONTROLLER)
@CrossOrigin(value = {CROSS_ORIGIN_LOCALHOST3000,CROSS_ORIGIN_ALL})
public class QuestionRestController {


}
