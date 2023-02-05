package com.cvictor.activitytrackerapp.Controller;

import com.cvictor.activitytrackerapp.DTO.TaskDTO;
import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {


    @Autowired
    TaskService taskService;


    //    Method that adds form input to the database and redirects to viewPost method handler
    @PostMapping("/addTask")
    public String addTask(TaskDTO taskDTO, HttpSession session ) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        taskService.createTask(userDTO, taskDTO);
        return "redirect:/viewTask";
    }


    //    Method handler retrieves all data from table and adds it to a model
    @GetMapping("/viewTask")
    public String viewAllTasks(Model model, HttpSession session ) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        model.addAttribute("tasks", taskService.getAllTasksOfUser(userDTO));
        return "tasks";
    }

    // Method handle enables launderer to delete specific customer entry
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id,  HttpSession session ) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        taskService.deleteTaskById(id, userDTO);
        return "redirect:/viewTask";
    }

    // Method handler enbles the launderer to edit a specific customer entry
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable ("id") long id, Model model) {
        model.addAttribute("taskToEdit", taskService.getTaskById(id));
        return "edit";

    }
        // Method handler for saving back edited post
        @PostMapping("/edittask")

        public String updateTask (@ModelAttribute("taskToEdit") TaskDTO taskDTO, HttpSession session ) {
            UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
            System.out.println(taskDTO.getTaskId());
            taskService.updateTask(userDTO, taskDTO);
            return "redirect:/viewTask";
        }

    }

