import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Policy from './policy';
import PolicyDetail from './policy-detail';
import PolicyUpdate from './policy-update';
import PolicyDeleteDialog from './policy-delete-dialog';

const PolicyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Policy />} />
    <Route path="new" element={<PolicyUpdate />} />
    <Route path=":id">
      <Route index element={<PolicyDetail />} />
      <Route path="edit" element={<PolicyUpdate />} />
      <Route path="delete" element={<PolicyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PolicyRoutes;
