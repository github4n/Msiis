package com.web.controller.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.model.UrlAccessPrefix;
import com.web.controller.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.web.soupe.dto.SoupeWebModel;
import com.web.soupe.roll.Roll;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = RollController.PATH)
public class RollController extends BaseController {
	protected static final String PATH = UrlAccessPrefix.FORE_GROUD_PATH+"roll";
	private Logger logger = Logger.getLogger(RollController.class);

    @RequiresUser
	@RequestMapping(value = "/kill/getNums", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public SoupeWebModel submit(
			@RequestParam(value = "red", required = false) String red,
			@RequestParam(value = "blue", required = false) String blue,
			@RequestParam(value = "num", required = false) String num,
			@RequestParam(value = "sum", required = false) String sum,
			@RequestParam(value = "sort", required = false) String sort,
			HttpServletRequest request) {
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try {
		    String[] reds =null ;
		    String[] blues =null;
		    String[] sums =new String[1] ;
		    int number =0;
		    if(StringUtils.isEmpty(sort)){
		      sort="-1";	
		    }
			if(!StringUtils.isEmpty(red)){
				reds =StringUtils.split(red, ",");
			}
			if(!StringUtils.isEmpty(blue)){
				blues =StringUtils.split(blue,",");
			}
           
			if(!StringUtils.isEmpty(num)){
				number =Integer.parseInt(num);
			}
			if(!StringUtils.isEmpty(sum)){
				sums =StringUtils.split(sum, "-");
			}
			int sum1 =21;
			int sum2 =200;
			if(sum!=null&&sums.length==2){
			   	sum1=Integer.parseInt(sums[0]);
				sum2=Integer.parseInt(sums[1]);
			}
				
		    
			List<Roll> rolls =this.serviceManager.getRollService().getNumbersOfKill(reds, blues, number, sum1, sum2, sort);
			soupewebModel.setData(rolls);
			soupewebModel.setSuccess(true);
		} catch (Exception ex) {
			logger.error("获取双色球信息出错，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		
		}
		return soupewebModel;
	}

	@RequestMapping(value="/kill/findNums",method=RequestMethod.GET)
	@ResponseBody
	public SoupeWebModel findNums(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request){
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try{
			if(!StringUtils.isEmpty(sort)){
				List<Roll> rolls =this.serviceManager.getRollService().findNums(sort, type);
				soupewebModel.setData(rolls);
			}
			soupewebModel.setSuccess(true);
		}catch(Exception ex){
			logger.error("获取双色球信息出错，，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		}
	     return soupewebModel;
		
	}


	@RequestMapping(value="/kill/deleteNums",method=RequestMethod.POST)
	@ResponseBody
	public SoupeWebModel delete(@RequestParam(value = "ids", required = false) String ids,
			HttpServletRequest request){
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try{
			if(!StringUtils.isEmpty(ids)){
                this.serviceManager.getRollService().deleteNums(ids);
			}
			soupewebModel.setSuccess(true);
		}catch(Exception ex){
			logger.error("获取双色球信息出错，，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		}
	     return soupewebModel;
		
	}
	
	
	
	@RequestMapping(value = "/choose/getNums", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public SoupeWebModel submitOfChoose(
			@RequestParam(value = "red", required = false) String red,
			@RequestParam(value = "blue", required = false) String blue,
			@RequestParam(value = "num", required = false) String num,
			@RequestParam(value = "sum", required = false) String sum,
			@RequestParam(value = "sort", required = false) String sort,
			HttpServletRequest request) {
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try {
		    String[] reds =null ;
		    String[] blues =null;
		    String[] sums =new String[1] ;
		    int number =0;
		    if(StringUtils.isEmpty(sort)){
		      sort="-1";	
		    }
			if(!StringUtils.isEmpty(red)){
				reds =StringUtils.split(red, ",");
			}
			if(!StringUtils.isEmpty(blue)){
				blues =StringUtils.split(blue,",");
			}
           
			if(!StringUtils.isEmpty(num)){
				number =Integer.parseInt(num);
			}
			if(!StringUtils.isEmpty(sum)){
				sums =StringUtils.split(sum, "-");
			}
			int sum1 =21;
			int sum2 =200;
			if(sum!=null&&sums.length==2){
			   	sum1=Integer.parseInt(sums[0]);
				sum2=Integer.parseInt(sums[1]);
			}
				
		    
			List<Roll> rolls =this.serviceManager.getRollService().getNumbersOfChoose(reds, blues, number, sum1, sum2, sort);
			soupewebModel.setData(rolls);
			soupewebModel.setSuccess(true);
		} catch (Exception ex) {
			logger.error("删除数据失败，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		
		}
		return soupewebModel;
	}
	
	
	@RequestMapping(value="/choose/findNums",method=RequestMethod.GET)
	@ResponseBody
	public SoupeWebModel findNumsOfChoose(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request){
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try{
			if(!StringUtils.isEmpty(sort)){
				List<Roll> rolls =this.serviceManager.getRollService().findNums(sort, type);
				soupewebModel.setData(rolls);
			}
			soupewebModel.setSuccess(true);
		}catch(Exception ex){
			logger.error("获取双色球信息出错，，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		}
	     return soupewebModel;
		
	}
	
	
	@RequestMapping(value="/choose/deleteNums",method=RequestMethod.POST)
	@ResponseBody
	public SoupeWebModel deleteOfChoose(@RequestParam(value = "ids", required = false) String ids,
			HttpServletRequest request){
		SoupeWebModel soupewebModel = new SoupeWebModel();
		try{
			if(!StringUtils.isEmpty(ids)){
                this.serviceManager.getRollService().deleteNums(ids);
			}
			soupewebModel.setSuccess(true);
		}catch(Exception ex){
			logger.error("删除数据失败 ，" + ex.getMessage(), ex);
			soupewebModel.setMessage(ex.getMessage());
		}
	     return soupewebModel;
		
	}
}
