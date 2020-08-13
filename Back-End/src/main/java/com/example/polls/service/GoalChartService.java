package com.example.polls.service;

import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalChartDTO;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.GoalChart;
import com.example.polls.repository.GoalChartRepository;
import com.example.polls.util.ObjectMapperUtils;
import com.example.polls.util.TimeHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Service
public class GoalChartService {

    @Autowired
    private GoalChartRepository goalChartRepository;

    public GoalChartDTO logTime(UUID id, TimeRequest time) {
        GoalChart updateGoal = goalChartRepository.findByGoalChartByGoalId(id);

        String addToTimeDone = updateGoal.getTimeDone();
        String formattedTime = new TimeHandler().formattedTime(time);

        if (addToTimeDone.isEmpty() || addToTimeDone == null) {
            updateGoal.setTimeDone(formattedTime);
            GoalChart goalToBeMapped = goalChartRepository.save(updateGoal);

            return ObjectMapperUtils.map(goalToBeMapped, GoalChartDTO.class);
        } else {
            try {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                TimeHandler time1 = TimeHandler.parse(formattedTime);
                TimeHandler time2 = TimeHandler.parse(addToTimeDone);
                String summedTime = time1.add(time2).toString();

                JSONArray jsonArray = updateGoal.getJsonData().getJSONArray("dataGraph");
                JSONArray newJSONArray = new JSONArray();
                JSONObject json = new JSONObject();

                for (int i = 0, size = jsonArray.length(); i < size; i++) {
                    JSONObject objectInArray = jsonArray.getJSONObject(i);

                    String[] elementNames = JSONObject.getNames(objectInArray);

                    JSONObject item = new JSONObject();
                    String date = "";
                    long expectedV = 0;
                    long timeExpectedToBeDone = 0;
                    boolean foundDate = false;
                    for (String elementName : elementNames) {
                        if (elementName.equals("name")) {
                            String value = objectInArray.getString(elementName);
                            if (currentDate.equals(value)) {
                                foundDate = true;
                            }
                            date = value;
                        }
                        if (elementName.equals("y")) {
                            long value = objectInArray.getLong(elementName);
                            if (foundDate == true) {
                                expectedV = Long.valueOf(time.getTime()).longValue() + value;
                                foundDate = false;
                            } else {
                                expectedV = value;
                            }
                        }
                        if (elementName.equals("x")) {
                            long value = objectInArray.getLong(elementName);
                            timeExpectedToBeDone = value;
                        }
                    }
                    item.put("name", date);
                    item.put("x", timeExpectedToBeDone);
                    item.put("y", expectedV);
                    newJSONArray.put(item);
                }

                json.put("dataGraph", newJSONArray);
                updateGoal.setJsonData(json);
                updateGoal.setTimeDone(summedTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        GoalChart goalToBeMapped = goalChartRepository.save(updateGoal);
        return ObjectMapperUtils.map(goalToBeMapped, GoalChartDTO.class);
    }


    public GoalChartDTO getOne(UUID id) {
        GoalChart goalChart = goalChartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chart", "id", id));
        return ObjectMapperUtils.map(goalChart, GoalChartDTO.class);
    }
}
