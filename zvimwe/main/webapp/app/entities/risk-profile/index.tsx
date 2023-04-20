import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RiskProfile from './risk-profile';
import RiskProfileDetail from './risk-profile-detail';
import RiskProfileUpdate from './risk-profile-update';
import RiskProfileDeleteDialog from './risk-profile-delete-dialog';

const RiskProfileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RiskProfile />} />
    <Route path="new" element={<RiskProfileUpdate />} />
    <Route path=":id">
      <Route index element={<RiskProfileDetail />} />
      <Route path="edit" element={<RiskProfileUpdate />} />
      <Route path="delete" element={<RiskProfileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RiskProfileRoutes;
