import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Condition from './condition';
import ConditionDetail from './condition-detail';
import ConditionUpdate from './condition-update';
import ConditionDeleteDialog from './condition-delete-dialog';

const ConditionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Condition />} />
    <Route path="new" element={<ConditionUpdate />} />
    <Route path=":id">
      <Route index element={<ConditionDetail />} />
      <Route path="edit" element={<ConditionUpdate />} />
      <Route path="delete" element={<ConditionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConditionRoutes;
