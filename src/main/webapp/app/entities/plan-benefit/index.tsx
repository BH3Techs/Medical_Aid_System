import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PlanBenefit from './plan-benefit';
import PlanBenefitDetail from './plan-benefit-detail';
import PlanBenefitUpdate from './plan-benefit-update';
import PlanBenefitDeleteDialog from './plan-benefit-delete-dialog';

const PlanBenefitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PlanBenefit />} />
    <Route path="new" element={<PlanBenefitUpdate />} />
    <Route path=":id">
      <Route index element={<PlanBenefitDetail />} />
      <Route path="edit" element={<PlanBenefitUpdate />} />
      <Route path="delete" element={<PlanBenefitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanBenefitRoutes;
