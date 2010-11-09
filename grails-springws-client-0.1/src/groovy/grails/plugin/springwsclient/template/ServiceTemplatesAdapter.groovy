/*
 * Copyright 2010 Luke Daley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugin.springwsclient.template

class ServiceTemplatesAdapter {

	static DEFINITIONS_PROPERTY = "wsclients"
	
	final grailsApplication
	final serviceClass
	final templateConfigs
	
	ServiceTemplatesAdapter(serviceClass, grailsApplication, TemplateConfigFactory configFactory) {
		this.serviceClass = serviceClass
		this.grailsApplication = grailsApplication
		
		this.templateConfigs = createConfigs(configFactory)
	}
	
	protected createConfigs(TemplateConfigFactory configFactory) {
		TemplateConfigBuilder.buildAll(grailsApplication, configFactory, getDefinitions(serviceClass))
	}
	
	void buildWith(TemplateBuilder builder) {
		templateConfigs.each {
			builder.build(it)
		}
	}
	
	static getDefinitions(serviceClass) {
		def definitions = serviceClass.getPropertyValue(DEFINITIONS_PROPERTY)
		if (definitions instanceof Closure) {
			definitions
		} else if (definitions != null) {
			throw new IllegalStateException("the '$DEFINITIONS_PROPERTY' property of a service must be a closure")
		} else {
			null
		}
	}
}