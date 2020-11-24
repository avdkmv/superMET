package com.unn.service.impl;

import java.util.Optional;

import com.unn.model.Calendar;
import com.unn.repository.CalendarRepo;
import com.unn.service.IScheduleService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {
  private final CalendarRepo calendarRepo;

  @Override
  public Optional<Calendar> createSchedule(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Calendar> deleteSchedule(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Calendar> findCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Calendar> modifyCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }

  @Override
  public Optional<Calendar> deleteCalendar(Long calendarId) {
    // TODO:  implement method
    return null;
  }
}
