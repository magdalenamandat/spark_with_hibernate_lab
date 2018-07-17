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
        //TODO: Add routes in here
        get("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/engineers/index.vtl");

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineer", engineers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
//
//        post("/managers", (req, res) -> {
//
//            int departmentId = Integer.parseInt(req.queryParams("department"));
//
//            Department department = DBHelper.find(departmentId, Department.class);
//
//            String firstName = req.queryParams("first-name");
//
//            String lastName = req.queryParams("last-name");
//
//            int salary = Integer.parseInt(req.queryParams("salary"));
//
//            double budget = Double.parseDouble(req.queryParams("budget"));
//
//
//            Manager manager = new Manager(firstName, lastName, salary, department, budget);
//
//            DBHelper.save(manager);
//
//            res.redirect("/managers");
//            return null;
//        }, new VelocityTemplateEngine());
//
//        get("/managers/:id", (req, res) -> {
//
//            Map<String, Object> model = new HashMap();
//
//            int managerId = Integer.parseInt(req.params(":id"));
//            Manager manager = DBHelper.find(managerId, Manager.class);
//            model.put("template", "templates/managers/show.vtl");
//            model.put("manager", manager);
//            return new ModelAndView(model, "templates/layout.vtl");
//
//        }, new VelocityTemplateEngine());
//
//        get("/managers/:id/edit", (req, res) -> {
//            int managerId = Integer.parseInt(req.params(":id"));
//
//            Manager manager = DBHelper.find(managerId, Manager.class);
//
//            Map<String, Object> model = new HashMap();
//            model.put("template", "templates/managers/update.vtl");
//
//            List<Department> departments = DBHelper.getAll(Department.class);
//            model.put("departments", departments);
//            model.put("manager", manager);
//
//            return new ModelAndView(model, "templates/layout.vtl");
//
//        },new VelocityTemplateEngine());
//
//        post("/managers/:id", (req, res) -> {
//            Manager manager = new Manager();
//
//            manager.setId(Integer.parseInt(req.params(":id")));
//            manager.setFirstName(req.queryParams("first-name"));
//            manager.setLastName(req.queryParams("last-name"));
//            manager.setSalary(Integer.parseInt(req.queryParams("salary")));
//            manager.setBudget(Double.parseDouble(req.queryParams("budget")));
//
//            int departmentId = Integer.parseInt(req.queryParams("department"));
//            Department department = DBHelper.find(departmentId, Department.class);
//            manager.setDepartment(department);
//
//            DBHelper.save(manager);
//            res.redirect("/managers");
//            return null;
//        }, new VelocityTemplateEngine());
//
//        post ("/managers/:id/delete", (req, res) -> {
//
//            int managerId = Integer.parseInt(req.params(":id"));
//
//            Manager manager = DBHelper.find(managerId, Manager.class);
//
//            DBHelper.delete(manager);
//
//            res.redirect("/managers");
//            return null;
//        }, new VelocityTemplateEngine());
//
   }
}
