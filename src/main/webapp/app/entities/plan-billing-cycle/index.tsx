import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PlanBillingCycle from './plan-billing-cycle';
import PlanBillingCycleDetail from './plan-billing-cycle-detail';
import PlanBillingCycleUpdate from './plan-billing-cycle-update';
import PlanBillingCycleDeleteDialog from './plan-billing-cycle-delete-dialog';

const PlanBillingCycleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PlanBillingCycle />} />
    <Route path="new" element={<PlanBillingCycleUpdate />} />
    <Route path=":id">
      <Route index element={<PlanBillingCycleDetail />} />
      <Route path="edit" element={<PlanBillingCycleUpdate />} />
      <Route path="delete" element={<PlanBillingCycleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanBillingCycleRoutes;
