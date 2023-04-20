import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BenefitClaimTracker from './benefit-claim-tracker';
import BenefitClaimTrackerDetail from './benefit-claim-tracker-detail';
import BenefitClaimTrackerUpdate from './benefit-claim-tracker-update';
import BenefitClaimTrackerDeleteDialog from './benefit-claim-tracker-delete-dialog';

const BenefitClaimTrackerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BenefitClaimTracker />} />
    <Route path="new" element={<BenefitClaimTrackerUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitClaimTrackerDetail />} />
      <Route path="edit" element={<BenefitClaimTrackerUpdate />} />
      <Route path="delete" element={<BenefitClaimTrackerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitClaimTrackerRoutes;
