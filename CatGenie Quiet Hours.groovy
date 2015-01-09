/**
 *  CatGenie Quiet Hours
 *
 *  Thanks to: james anderson, George Sudarkoff
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 import groovy.time.*
 
definition(
    name: "CatGenie Quiet Hours",
    namespace: "keithcroshaw",
    author: "keith croshaw",
    description: "CatGenie Quiet Hours",
    category: "My Apps",
    iconUrl: "https://graph.api.smartthings.com/api/devices/icons/st.Bath.bath5-icn?displaySize=2x",
    iconX2Url: "https://graph.api.smartthings.com/api/devices/icons/st.Bath.bath5-icn?displaySize=2x",
    iconX3Url: "https://graph.api.smartthings.com/api/devices/icons/st.Bath.bath5-icn?displaySize=2x")
 
 
preferences {
	section ("When this device stops drawing power") {
		input "meter", "capability.powerMeter", multiple: false, required: true
        input "DeviceNotRunning", "number", title: "Device not running when power drops below (W)", description: "8", required: true
        input "timeBegin", "time", title: "Time of Day to start"
        input "timeEnd", "time", title: "Time of Day to stop"
	}
    
    section("Turn off these switches..."){
		input "switches", "capability.switch", multiple: true
	}
}
 
def installed() {
	log.debug "Installed with settings: ${settings}"
    initialize()
}
 
def updated() {
	log.debug "Updated with settings: ${settings}"
    unsubscribe()
	initialize()
}
 
def initialize() {
    log.debug "time zone: ${location.timeZone}"
    subscribe(meter, "power", handler)
    def now = new Date()
    def startCheck = timeToday(timeBegin)
    def stopCheck = timeToday(timeEnd)
    def latestPower = meter.currentValue("power")
    
    log.debug "now: ${now}"
    log.debug "startCheck: ${startCheck}"
    log.debug "stopCheck: ${stopCheck}"
    
    def between = timeOfDayIsBetween(startCheck, stopCheck, now, location.timeZone)
    log.debug "between: ${between}"
    
	if (between && latestPower <= DeviceNotRunning){
	//if (between){
    	switches?.off()
		log.debug "fired init"
    }
}

def handler(evt) {

	log.debug "time zone: ${location.timeZone}"
    subscribe(meter, "power", handler)
    def now = new Date()
    def startCheck = timeToday(timeBegin)
    def stopCheck = timeToday(timeEnd)
    def latestPower = meter.currentValue("power")
    
    log.debug "now: ${now}"
    log.debug "startCheck: ${startCheck}"
    log.debug "stopCheck: ${stopCheck}"
    
    def between = timeOfDayIsBetween(startCheck, stopCheck, now, location.timeZone)
    log.debug "between: ${between}"
    
	if (between && latestPower <= DeviceNotRunning){
	//if (between){
    	switches?.off()
        log.debug "fired handler"
    }
}