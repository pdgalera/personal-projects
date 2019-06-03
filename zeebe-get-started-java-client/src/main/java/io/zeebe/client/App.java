package io.zeebe.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import io.zeebe.client.api.subscription.JobWorker;

/**
 * Pablo Galera 06/03/2019
 *
 */
public class App {

	public static void main(String[] args) throws InterruptedException {
		final ZeebeClient client = ZeebeClient.newClientBuilder()
				// change the contact point if needed
				.brokerContactPoint("127.0.0.1:26500").build();

		System.out.println("Connected.");
		final DeploymentEvent deployment = client.newDeployCommand().addResourceFromClasspath("order-process.bpmn")
				.send().join();

		final int version = deployment.getWorkflows().get(0).getVersion();
		System.out.println("Workflow deployed. Version: " + version);

		final Map<String, Object> data = new HashMap<>();
		data.put("orderId", 31243);
		data.put("orderItems", Arrays.asList(435, 182, 376));
		final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand().bpmnProcessId("order-process")
				.latestVersion().variables(data).send().join();

		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

		System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

		final JobWorker paymentServiceJobWorker = client.newWorker().jobType("payment-service")
				.handler((jobClient, job) -> {

					System.out.println("Collect money");

					final Map<String, Object> result = new HashMap<>();
					result.put("totalPrice", 46.50);

					jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
				}).open();

		Thread.sleep(1000);

		paymentServiceJobWorker.close();

		final JobWorker updateStockJobWorker = client.newWorker().jobType("update-stock").handler((jobClient, job) -> {

			System.out.println("Fetch Items");

			final Map<String, Object> result = new HashMap<>();
			result.put("totalPrice", 49.50);

			jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
		}).open();

		Thread.sleep(1000);

		updateStockJobWorker.close();

		final JobWorker sendOrderJobWorker = client.newWorker().jobType("send-order").handler((jobClient, job) -> {
			final Map<String, Object> variables = job.getVariablesAsMap();

			System.out.println("Ship Parcel");
			System.out.println("Process order: " + variables.get("orderId"));
			System.out.println("Order Items: " + variables.get("orderItems"));
			System.out.println("Total Price: " + variables.get("totalPrice"));

			final Map<String, Object> result = new HashMap<>();
			result.put("totalPrice", 49.50);

			jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
		}).open();

		Thread.sleep(1000);

		sendOrderJobWorker.close();
	}
}
