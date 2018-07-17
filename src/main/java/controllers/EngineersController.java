package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {

    public EngineersController(){
        this.setupEndpoints();
    }


    private void setupEndpoints() {
        // index
        get("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        // new
        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        // create
        post("/engineers", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));

            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("first-name");

            String lastName = req.queryParams("last-name");

            int salary = Integer.parseInt(req.queryParams("salary"));

            Engineer engineer = new Engineer(firstName, lastName, salary, department);

            DBHelper.save(engineer);

            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());

        // show
        get("/engineers/:id", (req, res) -> {

            Map<String, Object> model = new HashMap();

            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            model.put("template", "templates/engineers/show.vtl");
            model.put("engineer", engineer);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        // edit
        get("/engineers/:id/edit", (req, res) -> {
            int engineerId = Integer.parseInt(req.params(":id"));

            Engineer engineer = DBHelper.find(engineerId, Engineer.class);

            Map<String, Object> model = new HashMap();
            model.put("template", "templates/engineers/update.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("engineer", engineer);

            return new ModelAndView(model, "templates/layout.vtl");

        },new VelocityTemplateEngine());

        //update
        post("/engineers/:id", (req, res) -> {
            Engineer engineer = new Engineer();

            engineer.setId(Integer.parseInt(req.params(":id")));
            engineer.setFirstName(req.queryParams("first-name"));
            engineer.setLastName(req.queryParams("last-name"));
            engineer.setSalary(Integer.parseInt(req.queryParams("salary")));

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            engineer.setDepartment(department);

            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());

        //delete
        post ("/engineers/:id/delete", (req, res) -> {

            int engineerId = Integer.parseInt(req.params(":id"));

            Engineer engineer = DBHelper.find(engineerId, Engineer.class);

            DBHelper.delete(engineer);

            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());

    }


}