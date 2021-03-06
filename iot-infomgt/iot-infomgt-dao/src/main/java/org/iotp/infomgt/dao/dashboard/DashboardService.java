/*******************************************************************************
 * Copyright 2017 osswangxining@163.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/**
 * Copyright © 2016-2017 The Thingsboard Authors
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
package org.iotp.infomgt.dao.dashboard;

import org.iotp.infomgt.data.Dashboard;
import org.iotp.infomgt.data.DashboardInfo;
import org.iotp.infomgt.data.id.CustomerId;
import org.iotp.infomgt.data.id.DashboardId;
import org.iotp.infomgt.data.id.TenantId;
import org.iotp.infomgt.data.page.TextPageData;
import org.iotp.infomgt.data.page.TextPageLink;

import com.google.common.util.concurrent.ListenableFuture;

public interface DashboardService {
    
    Dashboard findDashboardById(DashboardId dashboardId);

    ListenableFuture<Dashboard> findDashboardByIdAsync(DashboardId dashboardId);

    DashboardInfo findDashboardInfoById(DashboardId dashboardId);

    ListenableFuture<DashboardInfo> findDashboardInfoByIdAsync(DashboardId dashboardId);

    Dashboard saveDashboard(Dashboard dashboard);

    Dashboard assignDashboardToCustomer(DashboardId dashboardId, CustomerId customerId);

    Dashboard unassignDashboardFromCustomer(DashboardId dashboardId);

    void deleteDashboard(DashboardId dashboardId);

    TextPageData<DashboardInfo> findDashboardsByTenantId(TenantId tenantId, TextPageLink pageLink);

    void deleteDashboardsByTenantId(TenantId tenantId);

    TextPageData<DashboardInfo> findDashboardsByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TextPageLink pageLink);

    void unassignCustomerDashboards(TenantId tenantId, CustomerId customerId);

}
