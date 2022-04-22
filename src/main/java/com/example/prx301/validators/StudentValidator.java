package com.example.prx301.validators;

import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StudentValidator {
    Document document;
    XPath xPath;

    @Autowired
    public StudentValidator(Document document, XPath xPath) {
        this.document = document;
        this.xPath = xPath;
    }

    public StudentValidationResult checkStudent(StudentDTO dto) throws XPathExpressionException {
        System.out.println("DTO: " + dto);
        StudentValidationResult validationResult = null;
        String firstName = dto.getFirstName();
        String lastName = dto.getLastName();
        String email = dto.getEmail();
        String dob = dto.getDob();
        String phone = dto.getPhoneNumber();
        String majorId="";
        if(dto.getMajor() != null){
            majorId = dto.getMajor().getId();
        }
        EStudentStatus status = EStudentStatus.valueOfStr(dto.getStatus());
        Pattern pattern = null;
        boolean hasErr = false;
        String firstNameError ="", lastNameError="",dobErr ="",majorErr = "", emailErr = "",phoneErr="";
        if(firstName != null && "".equals(firstName)){
            hasErr = true;
            firstNameError = "First name must not be empty";
        }else{
            pattern = Pattern.compile("[A-Za-z]{2,60}");
            Matcher matcher = pattern.matcher(firstName);
            if(!matcher.matches()){
                firstNameError = "First name must only contain a-z A-Z";
                hasErr = true;
            }
        }
        if(lastName != null &&"".equals(lastName)){
            hasErr = true;
            lastNameError = "Last name must not be empty";
        }else{
            pattern = Pattern.compile("[A-Za-z]{2,60}");
            Matcher matcher = pattern.matcher(lastName);
            if(!matcher.matches()){
                lastNameError = "Last name must only contain a-z A-Z";
                hasErr = true;
            }
        }//end check lastname.

        if(dob != null &&"".equals(dob)){
            hasErr = true;
            firstNameError = "Dob must not be empty";
        }else{
            pattern = Pattern.compile("([0-9]{4,4})-(0[1-9]|1[012])-(0[1-9]|[1-2][0-9]|3[0-1])");
            Matcher matcher = pattern.matcher(dob);
            if(!matcher.matches()){
                dobErr = "Dob must have format yyyy-mm-dd";
                hasErr = true;
            }
        }//end check dob

        if(email == null ||(email != null && "".equals(email))){
            hasErr = true;
            emailErr = "Email must not be empty";
        }else{
            pattern = Pattern.compile("^[a-zA-Z]([a-zA-Z0-9]{1,})@[a-zA-Z.]{1,}[a-zA-Z]$");
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()){
                emailErr = "Email wrong format";
                hasErr = true;
            }
        }//end check email

        if(phone == null ||phone.isBlank()){
            hasErr = true;
            phoneErr = "Phone must not empty";
        }{
            pattern = Pattern.compile("[0-9]{5,11}|[(][0-9]{3,3}[)] [0-9]{3,3}-[0-9]{4,4}");
            Matcher matcher = pattern.matcher(phone);
            phoneErr = matcher.matches()? "":"Phone number has wrong format";
        }

        if(majorId == null ||(majorId != null && "".equals(majorId))){
            hasErr = true;
            majorErr = "Please select major";
        }else{
            boolean foundMajor = false;
            NodeList majorIds = (NodeList) xPath.evaluate("//majorId", document, XPathConstants.NODESET);
            for (int i = 0; i < majorIds.getLength(); i++) {
               if(majorIds.item(i).getTextContent().trim().equals(majorId)){
                   foundMajor = true;
                   i = majorIds.getLength() + 1;
               }
            }
            if(!foundMajor){
                hasErr = true;
                majorErr = "major not found";
            }
        }
        if(hasErr){
            validationResult = new StudentValidationResult();
            validationResult.setDobErr(dobErr);
            validationResult.setEmailErr(emailErr);
            validationResult.setFirstNameError(firstNameError);
            validationResult.setLastNameError(lastNameError);
            validationResult.setPhoneErr(phoneErr);
            validationResult.setMajorErr(majorErr);
        }
        return validationResult;
    }
}
