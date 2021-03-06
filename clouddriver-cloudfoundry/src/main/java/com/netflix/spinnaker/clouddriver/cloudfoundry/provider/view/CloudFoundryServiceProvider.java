/*
 * Copyright 2018 Pivotal, Inc.
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

package com.netflix.spinnaker.clouddriver.cloudfoundry.provider.view;

import com.netflix.spinnaker.clouddriver.cloudfoundry.CloudFoundryCloudProvider;
import com.netflix.spinnaker.clouddriver.cloudfoundry.model.CloudFoundryService;
import com.netflix.spinnaker.clouddriver.model.ServiceInstance;
import com.netflix.spinnaker.clouddriver.model.ServiceProvider;
import com.netflix.spinnaker.clouddriver.cloudfoundry.security.CloudFoundryCredentials;
import com.netflix.spinnaker.clouddriver.security.AccountCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CloudFoundryServiceProvider implements ServiceProvider {
  final private AccountCredentialsProvider accountCredentialsProvider;

  @Autowired
  public CloudFoundryServiceProvider(AccountCredentialsProvider accountCredentialsProvider) {
    this.accountCredentialsProvider = accountCredentialsProvider;
  }

  @Override
  public Collection<CloudFoundryService> getServices(String account, String region) {
    CloudFoundryCredentials credentials = (CloudFoundryCredentials) accountCredentialsProvider.getCredentials(account);
    return credentials
      .getCredentials()
      .getServiceInstances()
      .findAllServicesByRegion(region);
  }

  @Override
  public ServiceInstance getServiceInstance(String account, String region, String serviceInstanceName) {
    CloudFoundryCredentials credentials = (CloudFoundryCredentials) accountCredentialsProvider.getCredentials(account);
    return credentials
      .getCredentials()
      .getServiceInstances()
      .getServiceInstance(region, serviceInstanceName);
  }

  @Override
  public String getCloudProvider() {
    return CloudFoundryCloudProvider.ID;
  }
}
