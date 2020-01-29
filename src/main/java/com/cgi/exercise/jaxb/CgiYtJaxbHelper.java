package com.cgi.exercise.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cgi.exercise.yt.domain.YtSearchVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CgiYtJaxbHelper {
	
	public String getXmlMessage(YtSearchVO searchVO) {
		String ytXmlMsg = "";
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(YtSearchVO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(searchVO, stringWriter);
			ytXmlMsg = stringWriter.toString();
		} catch (Exception e) {
			log.error("Error while creating XML message from SearchVO", e);
		}
		return ytXmlMsg;
	}
	
	public YtSearchVO convertXmlMessage(String ytXmlMsg) {
		YtSearchVO ytSearchVO = new YtSearchVO();
		try {
			if (!StringUtils.isEmpty(ytXmlMsg)) {
				ytSearchVO = unMarshalling(ytXmlMsg);
			} else {
				setSearchVODefaults(ytSearchVO);
			}
		} catch (Exception e) {
			log.error("Error while converting XML message into SearchVO", e);
			setSearchVODefaults(ytSearchVO);
		}
		return ytSearchVO;
	}

	private YtSearchVO unMarshalling(String ytXmlMsg) throws JAXBException {
		YtSearchVO ytSearchVO;
		JAXBContext jaxbContext = JAXBContext.newInstance(YtSearchVO.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(ytXmlMsg);
		ytSearchVO = (YtSearchVO) unmarshaller.unmarshal(reader);
		return ytSearchVO;
	}
	
	private void setSearchVODefaults(YtSearchVO searchVO) {
		searchVO.setYtVideoTitle("");
		searchVO.setYtWatchUrl("");
	}

}
