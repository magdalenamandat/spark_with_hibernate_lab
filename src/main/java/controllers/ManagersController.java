package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

    public ManagersController(){
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        //TODO: Add routes in here
        get("/managers", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/managers/index.vtl");

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/managers", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));

            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("first-name");

            String lastName = req.queryParams("last-name");

            int salary = Integer.parseInt(req.queryParams("salary"));

            double budget = Double.parseDouble(req.queryParams("budget"));


            Manager manager = new Manager(firstName, lastName, salary, department, budget);

            DBHelper.save(manager);

            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());


        get("/managers/:id/edit", (req, res) -> {
            int managerId = Integer.parseInt(req.params(":id"));

            Manager manager = DBHelper.find(managerId, Manager.class);

            Map<String, Object> model = new HashMap();
            model.put("template", "templates/managers/update.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("manager", manager);

            return new ModelAndView(model, "templates/layout.vtl");

        },new VelocityTemplateEngine());

        post("/managers/:id", (req, res) -> {
            Manager manager = new Manager();

            manager.setId(Integer.parseInt(req.params(":id")));
            manager.setFirstName(req.queryParams("first-name"));
            manager.setLastName(req.queryParams("last-name"));
            manager.setSalary(Integer.parseInt(req.queryParams("salary")));
            manager.setBudget(Double.parseDouble(req.queryParams("budget")));

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            manager.setDepartment(department);

            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

    }
}