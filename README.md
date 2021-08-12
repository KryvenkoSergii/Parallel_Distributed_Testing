# Parallel_Distributed_Testing
Parallel &amp; Distributed testing with TestNG and Selenium Grid

Setup Selenium Grid hubconfig.json file:
	{
	  "port": 4444,
	  "newSessionWaitTimeout": -1,
	  "servlets" : [],
	  "withoutServlets": [],
	  "custom": {},
	  "capabilityMatcher": "org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
	  "throwOnCapabilityNotPresent": true,
	  "cleanUpCycle": 5000,
	  "role": "hub",
	  "debug": false,
	  "browserTimeout": 0,
	  "timeout": 1800
	}

Setup Selenium Grid nodeconfig1.json file:
	{
	  "capabilities":
	  [
		{
		  "browserName": "firefox",
		  "version": "90",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		},
		{
		  "browserName": "chrome",
		  "version": "92",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		},
		{
		  "browserName": "edge",
		  "version": "92",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		}
	  ],
	  "proxy": "org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
	  "maxSession": 20,
	  "port": 5566,
	  "register": true,
	  "registerCycle": 5000,
	  "hub": "http://localhost:4444",
	  "nodeStatusCheckTimeout": 5000,
	  "nodePolling": 5000,
	  "role": "node",
	  "unregisterIfStillDownAfter": 60000,
	  "downPollingLimit": 2,
	  "debug": false,
	  "servlets" : [],
	  "withoutServlets": [],
	  "custom": {}
	}

Setup Selenium Grid nodeconfig2.json file:
	{
	  "capabilities":
	  [
		{
		  "browserName": "firefox",
		  "version": "90",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		},
		{
		  "browserName": "chrome",
		  "version": "92",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		},
		{
		  "browserName": "edge",
		  "version": "92",
		  "maxInstances": 10,
		  "seleniumProtocol": "WebDriver"
		}
	  ],
	  "proxy": "org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
	  "maxSession": 20,
	  "port": 5567,
	  "register": true,
	  "registerCycle": 5000,
	  "hub": "http://localhost:4444",
	  "nodeStatusCheckTimeout": 5000,
	  "nodePolling": 5000,
	  "role": "node",
	  "unregisterIfStillDownAfter": 60000,
	  "downPollingLimit": 2,
	  "debug": false,
	  "servlets" : [],
	  "withoutServlets": [],
	  "custom": {}
	}

Create an execution SeleniumGrid run_SeleniumServer.bat file:
	@echo off
	echo Running Selenium server with configurations (hubconfig.json)
	start java -jar selenium-server-standalone-3.141.59.jar -role hub -hubConfig hubconfig.json
	start java -Dwebdriver.chrome.driver="chromedriver.exe" -Dwebdirver.gecko.driver="geckodriver.exe" -Dwebdirver.edge.driver="msedgedriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig nodeconfig1.json
	start java -Dwebdriver.chrome.driver="chromedriver.exe" -Dwebdirver.gecko.driver="geckodriver.exe" -Dwebdirver.edge.driver="msedgedriver.exe" -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig nodeconfig2.json
	timeout 3
	start "" http://localhost:4444/grid/console