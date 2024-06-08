package com.example.Book.controller;

import com.example.Book.Model.Course;
import com.example.Book.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("course", new Course());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("course") Course newCourse, Model model) {
        if (newCourse.getLectureName() == null ||
                newCourse.getPlace() == null ||
                newCourse.getStartDate() == null) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin để tạo khóa học.");
            return "create";
        }
        courseService.add(newCourse);
        return "redirect:/course/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("course", course);
            return "edit";
        } else {
            return "redirect:/course/list";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("course") Course updatedCourse, Model model) {
        if (updatedCourse.getLectureName() == null ||
                updatedCourse.getPlace() == null ||
                updatedCourse.getStartDate() == null) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin để cập nhật khóa học.");
            return "edit";
        }
        String message = courseService.update(updatedCourse);
        if (message.equals("Cập nhật khóa học thành công.")) {
            return "redirect:/course/list";
        } else {
            model.addAttribute("error", message);
            return "edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return "redirect:/course/list";
    }
    @GetMapping("/search")
    public String searchCourses(@RequestParam("query") String query, Model model) {
        List<Course> courses = courseService.searchCourseByName(query);
        model.addAttribute("courses", courses);

        // Thêm đoạn code sau để truyền giá trị tìm kiếm vào trang kết quả
        model.addAttribute("searchQuery", query);

        return "list";
    }
}
