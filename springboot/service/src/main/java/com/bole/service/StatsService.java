package com.bole.service;

import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

@Service
public class StatsService {

    public String getMemoryStatus(String message) {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        String memoryStats = "";

        String init = String.format(
                message + "\n",
                "Initial: %.2f GB \n",
                (double)memoryBean.getHeapMemoryUsage().getInit() /1073741824);
        String usedHeap = String.format("Used: %.2f GB \n",
                (double)memoryBean.getHeapMemoryUsage().getUsed() /1073741824);
        String maxHeap = String.format("Max: %.2f GB \n",
                (double)memoryBean.getHeapMemoryUsage().getMax() /1073741824);
        String committed = String.format("Committed: %.2f GB \n",
                (double)memoryBean.getHeapMemoryUsage().getCommitted() /1073741824);
        memoryStats += init;
        memoryStats += usedHeap;
        memoryStats += maxHeap;
        memoryStats += committed;

        return memoryStats;
    }
}
