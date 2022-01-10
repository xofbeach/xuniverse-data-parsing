package com.xuniverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main {

    public static void main(String[] args) throws Exception {
    	//http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?LAWD_CD=11110&DEAL_YMD=201512&serviceKey=서비스키
        StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=MvP5mStvWZx5qmz9h0LauodR5vQUFAy7rfouQ9nYEEVBLztEcZdr2Ul6FZn7LT2SdKwhSq9sggfB%2B%2F4qFyraKw%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("201512", "UTF-8")); /*계약월*/
        URL url = new URL(urlBuilder.toString());
        System.out.println("url: "+url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;


        // xml 파싱하기
        InputSource is = new InputSource(new StringReader(sb.toString()));
        builder = factory.newDocumentBuilder();
        doc = builder.parse(is);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        // XPathExpression expr = xpath.compile("/response/body/items/item");
        XPathExpression expr = xpath.compile("//items/item");
        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        AptRealTransactionDetailVo vo = new AptRealTransactionDetailVo();
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            for (int j = 0; j < child.getLength(); j++) {
                Node node = child.item(j);
                System.out.println("j: "+j);
                System.out.println("현재 노드 이름 : " + node.getNodeName());
//                System.out.println("현재 노드 타입 : " + node.getNodeType());
                System.out.println("현재 노드 값 : " + node.getTextContent());
//                System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
//                System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
                System.out.println("");


                if(j==0)vo.setTransactionAmount(node.getTextContent().trim());
                if(j==1)vo.setConstructionYeae(node.getTextContent().trim());
                if(j==2)vo.setDealYear(node.getTextContent().trim());
                if(j==3)vo.setRoadName(node.getTextContent().trim());
                if(j==4)vo.setRoadNameBuildingMainNumber(node.getTextContent().trim());
                if(j==5)vo.setRoadNameBuildingSubNumber(node.getTextContent().trim());
                if(j==6)vo.setRoadNameDivisionCode(node.getTextContent().trim());
                if(j==7)vo.setRoadNameSerialNumberCode(node.getTextContent().trim());
                if(j==8)vo.setRoadNameGroundBasementCode(node.getTextContent().trim());
                if(j==9)vo.setRoadNameCode(node.getTextContent().trim());
                if(j==10)vo.setLegalDong(node.getTextContent().trim());
                if(j==11)vo.setLegalDongMainCode(node.getTextContent().trim());
                if(j==12)vo.setLegalDongSubCode(node.getTextContent().trim());
                if(j==13)vo.setLegalDongDivisionCode1(node.getTextContent().trim());
                if(j==14)vo.setLegalDongDivisionCode2(node.getTextContent().trim());
                if(j==15)vo.setLegalDongLotNumberCode(node.getTextContent().trim());
                if(j==16)vo.setApartmentName(node.getTextContent().trim());
                if(j==17)vo.setDealMonth(node.getTextContent().trim());
                if(j==18)vo.setDealDay(node.getTextContent().trim());
                // 19 일련번호 제외
                if(j==20)vo.setExclusiveArea(node.getTextContent().trim());
                if(j==21)vo.setLotNumber(node.getTextContent().trim());
                if(j==22)vo.setRegionCode(node.getTextContent().trim());
                if(j==23)vo.setFloor(node.getTextContent().trim());
            }
            System.out.println("112");
        }


    }
}
