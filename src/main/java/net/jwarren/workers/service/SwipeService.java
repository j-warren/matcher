package net.jwarren.workers.service;

import net.jwarren.workers.misc.UpstreamException;
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
    private static final String USER_MSG_DATA_UNAVAILABLE = "Sorry, we can't seem to access %s data right now. Please try again later.";

    private static final Logger LOGGER = LoggerFactory.getLogger(SwipeService.class);

    private final RestTemplate restTemplate;

    public SwipeService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Job> findAllJobs() throws UpstreamException {
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
            throw new UpstreamException(String.format(USER_MSG_DATA_UNAVAILABLE, "job"));
        }

        return null;
    }

    public List<Worker> findAllWorkers() throws UpstreamException {
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
            throw new UpstreamException(String.format(USER_MSG_DATA_UNAVAILABLE, "worker"));
        }

        return null;
    }

}
