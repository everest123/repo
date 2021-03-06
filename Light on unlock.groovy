/**
 *  handler test
 *
 *  Copyright 2015 Keith Croshaw
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
definition(
    name: "handler test",
    namespace: "keithcroshaw",
    author: "Keith Croshaw",
    description: "handler test",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Monitor this lock") {
        input "lock", "capability.lock", title:"Which lock?"
    }
    
    section("Turn on this switch") {
        input "switch1", "capability.switch", title:"Which switch?"
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
 
 subscribe(lock, "lock.locked", onLocked)
 subscribe(lock, "lock.unlocked", onUnlocked)

}

def onLocked(evt) {
	log.debug "Locked"
    switch1?.off()
}

def onUnlocked(evt) {
	log.debug "Unocked"
    switch1?.on()
}
