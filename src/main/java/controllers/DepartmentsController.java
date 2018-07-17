package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController(){
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/departments/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
//            List<Department> departments = DBHelper.getAll(Department.class);
//            model.put("departments", departments);
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/departments", (req, res) -> {

            String title = req.queryParams("title");

            Department department = new Department(title);

            DBHelper.save(department);

            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());

        get("/departments/:id", (req, res) -> {

            Map<String, Object> model = new HashMap();

            int departmentId = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            model.put("template", "templates/departments/show.vtl");
            model.put("department", department);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        get("/departments/:id/edit", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));

            Department department = DBHelper.find(departmentId, Department.class);

            Map<String, Object> model = new HashMap();
            model.put("template", "templates/departments/update.vtl");

//            List<Department> departments = DBHelper.getAll(Department.class);
//            model.put("departments", departments);
            model.put("department", department);

            return new ModelAndView(model, "templates/layout.vtl");

        },new VelocityTemplateEngine());

        post("/departments/:id", (req, res) -> {
            Department department = new Department();

            department.setId(Integer.parseInt(req.params(":id")));
            department.setTitle(req.queryParams("title"));

            DBHelper.save(department);
            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());

        post ("/departments/:id/delete", (req, res) -> {

            int departmentId = Integer.parseInt(req.params(":id"));

            Department department = DBHelper.find(departmentId, Department.class);

            DBHelper.delete(department);

            res.redirect("/departments");
            return null;
        }, new VelocityTemplateEngine());

    }


}
