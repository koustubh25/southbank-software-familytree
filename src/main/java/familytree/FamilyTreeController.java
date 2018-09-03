package familytree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FamilyTreeController implements ErrorController {

    @Autowired
    private FamilyTreeService service;

    @RequestMapping(value="/getPeople")
    public List<String> getPeople(@RequestParam(value="name", required = true) String name,
                                  @RequestParam(value = "relation", required = true) String relation){

        try {
            return service.getPeopleBasedOnRelation(name, relation);
        }
        catch(RuntimeException e){
            throw e;
        }
    }



    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        // Actual exception handling
    }

    @RequestMapping("/*")
//    public void handleRequest() {
//        throw new RuntimeException("Url path does not exist. The only path that exists is /getPeople");
//    }
    public ResponseEntity invalidUrl(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
                        + "<div>Exception Message: <b>%s</b></div><body></html>",
                statusCode, exception==null? "N/A": exception.getMessage());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }


}
