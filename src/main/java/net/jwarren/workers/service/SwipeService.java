package net.jwarren.workers.service;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SwipeService {

    private static final String JOB_URL = "http://test.swipejobs.com/api/jobs";
    private static final String WORKER_URL = "http://test.swipejobs.com/api/workers";
    private static final Logger LOGGER = LoggerFactory.getLogger(SwipeService.class);

    private final RestTemplate restTemplate;

    public SwipeService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Job> findAllJobs() {
        LOGGER.info("GET {}", JOB_URL);
        try {
            ResponseEntity<Job[]> jobs = restTemplate.getForEntity(JOB_URL, Job[].class);
            Job[] body = jobs.getBody();
            if (body != null) {
                LOGGER.info("Got {} jobs", body.length);
                return Arrays.asList(body);
            }
            LOGGER.warn("Got no jobs");
        } catch (HttpClientErrorException exception) {
            LOGGER.error("GET request failed, returning null:", exception);
        }

        return null;
    }

    public List<Worker> findAllWorkers() {
        LOGGER.info("GET {}", WORKER_URL);
        try {
            ResponseEntity<Worker[]> jobs = restTemplate.getForEntity(WORKER_URL, Worker[].class);
            Worker[] body = jobs.getBody();
            if (body != null) {
                LOGGER.info("Got {} workers", body.length);
                return Arrays.asList(body);
            }
            LOGGER.warn("Got no workers");
        } catch (HttpClientErrorException exception) {
            LOGGER.error("GET request failed, returning null:", exception);
        }

        return null;
    }

}
