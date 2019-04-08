package com.example.goalranger.Models;

import com.google.gson.annotations.SerializedName;

public class Task {
 private String goal_id;
 @SerializedName("task_title")
 private String task_title;
 @SerializedName("description")
 private String description;
 @SerializedName("begin_time")
 private String begin_time;
 @SerializedName("begin_date")
 private String begin_date;
 @SerializedName("due_time")
 private String due_time;
 @SerializedName("due_date")
 private String due_date;
 private String reminder;
 private float task_status;
 private String updated_at;
 private String created_at;
 private float id;

 // Getter Methods

 public String getGoal_id() {
  return goal_id;
 }

 public String getTask_title() {
  return task_title;
 }

 public String getDescription() {
  return description;
 }

 public String getBegin_time() {
  return begin_time;
 }

 public String getBegin_date() {
  return begin_date;
 }

 public String getDue_time() {
  return due_time;
 }

 public String getDue_date() {
  return due_date;
 }

 public String getReminder() {
  return reminder;
 }

 public float getTask_status() {
  return task_status;
 }

 public String getUpdated_at() {
  return updated_at;
 }

 public String getCreated_at() {
  return created_at;
 }

 public float getId() {
  return id;
 }

 // Setter Methods

 public void setGoal_id(String goal_id) {
  this.goal_id = goal_id;
 }

 public void setTask_title(String task_title) {
  this.task_title = task_title;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public void setBegin_time(String begin_time) {
  this.begin_time = begin_time;
 }

 public void setBegin_date(String begin_date) {
  this.begin_date = begin_date;
 }

 public void setDue_time(String due_time) {
  this.due_time = due_time;
 }

 public void setDue_date(String due_date) {
  this.due_date = due_date;
 }

 public void setReminder(String reminder) {
  this.reminder = reminder;
 }

 public void setTask_status(float task_status) {
  this.task_status = task_status;
 }

 public void setUpdated_at(String updated_at) {
  this.updated_at = updated_at;
 }

 public void setCreated_at(String created_at) {
  this.created_at = created_at;
 }

 public void setId(float id) {
  this.id = id;
 }
}
