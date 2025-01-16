package com.example.securityApiJWT.Controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Authentication/Manager")
@Tag(name = "Management")
public class ManagerController {

    @Operation(
            summary = "GET Endpoint for manager",
            description = "This is the description for the GET Endpoint management",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid/Unauthorized token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public String viewData(){
        return "Only admin and manager have right and obligations to view list of data";
    }

    @PostMapping
    public String insertData(){
        return "Only admin and manager have right and obligations to insert data";
    }

    @PutMapping
    public String updateData(){
        return "Only admin and manager have right and obligations to update data";
    }

    @Hidden
    @DeleteMapping
    public String deleteData(){
        return "Only admin and manager have right and obligations to delete data";
    }
}
