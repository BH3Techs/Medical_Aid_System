import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Plans from './plans';
import PlansDetail from './plans-detail';
import PlansUpdate from './plans-update';
import PlansDeleteDialog from './plans-delete-dialog';

const PlansRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Plans />} />
    <Route path="new" element={<PlansUpdate />} />
    <Route path=":id">
      <Route index element={<PlansDetail />} />
      <Route path="edit" element={<PlansUpdate />} />
      <Route path="delete" element={<PlansDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlansRoutes;
