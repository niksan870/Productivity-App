package com.example.polls.service;

import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalChartDTO;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Goal;
import com.example.polls.model.GoalChart;
import com.example.polls.model.User;
import com.example.polls.repository.GoalChartRepository;
import com.example.polls.util.ObjectMapperUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GoalChartService {

    @Autowired
    private GoalChartRepository goalChartRepository;

    public GoalChartDTO logTime(UUID id, TimeRequest time) {
        GoalChart updateGoal = goalChartRepository.findByGoalChartByGoalId(id);

        float addToTimeDone = updateGoal.getTimeDone();
        float timeToAdd = time.getTime();
        float summedTime = addToTimeDone + timeToAdd;

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        JSONArray jsonArray = updateGoal.getJsonData().getJSONArray("dataGraph");
        JSONObject json = new JSONObject();

        for (int i = 0, size = jsonArray.length(); i < size; i++) {
            JSONObject objectInArray = jsonArray.getJSONObject(i);
            String[] elementNames = JSONObject.getNames(objectInArray);
            boolean foundDate = false;
            for (String elementName : elementNames) {
                if (elementName.equals("name")) {
                    String value = objectInArray.getString(elementName);
                    if (currentDate.equals(value)) {
                        foundDate = true;
                    }
                }
                if (elementName.equals("timeDone")) {
                    float value = objectInArray.getFloat(elementName);
                    if (foundDate == true) {
                        objectInArray.put(elementName, value + timeToAdd);
                        foundDate = false;
                    }
                }
            }
        }

        json.put("dataGraph", jsonArray);
        updateGoal.setJsonData(json);
        updateGoal.setTimeDone(summedTime);

        GoalChart goalToBeMapped = goalChartRepository.save(updateGoal);
        return ObjectMapperUtils.map(goalToBeMapped, GoalChartDTO.class);
    }

    public Page<GoalChartDTO> getGoalCharts(int pageNo, int pageSize, UUID id) {
        validatePageNumberAndSize(pageNo, pageSize);

        List<GoalChart> goalCharts = goalChartRepository.findAllById(id);
        List<GoalChartDTO> goalChartDTOS = ObjectMapperUtils.mapAll(goalCharts, GoalChartDTO.class);

        return new PageImpl<>(goalChartDTOS);
    }

    public GoalChartDTO getOne(UUID id) {
        GoalChart goalChart = goalChartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chart", "id", id));
        return ObjectMapperUtils.map(goalChart, GoalChartDTO.class);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
    }
}
