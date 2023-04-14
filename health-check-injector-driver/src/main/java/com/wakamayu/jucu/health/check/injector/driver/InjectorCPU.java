package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import com.wakamayu.jucu.health.check.injector.interfaces.Driver;

import jakarta.ejb.Stateful;

import javax.inject.Named;
import javax.inject.Singleton;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import com.sun.management.OperatingSystemMXBean;
/**
 *
 * @author carlos
 */
@Named("CPU")
@Stateful
public class InjectorCPU implements Driver {

//    private Gauge osCPUUsedRatio = Gauge.build().name("os_memory_used_ratio")
//            .help("The ratio of the systems memory that is currently used (values are 0-1)").register();
//
//    private Summary osHandlerCPULatencySeconds = Summary.build()
//            .name("os_handler_cpu_latency_seconds")
//            .help("request latency in seconds")
//            .register();

	private long prevUpTime = 0;

	private long prevProcessCpuTime = 0;

	private final double DEFAULT_DOUBLE_VALUE = -1.0;

	private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

	@Override
	public TracerModel execute(TracerModel model) {

		double percent = getProcessCpuUsage();

		double max = model.getMax();

		double min = model.getMin();

		model.setRate(percent);
		
		if(percent <= min && percent <= max) {
			model.setStatus(TypeStatus.AVAILABILITY);
		}else if ( percent > min && percent <= max) {
			model.setStatus(TypeStatus.CRITICAL);			
		}else {
			model.setStatus(TypeStatus.UNAVAILABILITY);
		}

		return model;
	}

	public double getProcessCpuUsage() {
		double processCpuUsage;
		try {
			RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
			OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
					.getOperatingSystemMXBean();

			long upTime = runtimeMXBean.getUptime();

			long processCpuTime = operatingSystemMXBean.getProcessCpuTime();

			int numberOfCpus = operatingSystemMXBean.getAvailableProcessors();
			
			if (prevUpTime > 0L && upTime > prevUpTime) {
				long elapsedCpu = processCpuTime - prevProcessCpuTime;
				
				long elapsedTime = upTime - prevUpTime;
				
				processCpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * numberOfCpus));
			} else {
				processCpuUsage = DEFAULT_DOUBLE_VALUE;
			}
			
			prevUpTime = upTime;
			prevProcessCpuTime = processCpuTime;
		} catch (Exception e) {
			e.printStackTrace();
			processCpuUsage = DEFAULT_DOUBLE_VALUE;
		}
		return processCpuUsage;
	}
}
