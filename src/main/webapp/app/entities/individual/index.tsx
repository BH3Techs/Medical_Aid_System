import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Individual from './individual';
import IndividualDetail from './individual-detail';
import IndividualUpdate from './individual-update';
import IndividualDeleteDialog from './individual-delete-dialog';

const IndividualRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Individual />} />
    <Route path="new" element={<IndividualUpdate />} />
    <Route path=":id">
      <Route index element={<IndividualDetail />} />
      <Route path="edit" element={<IndividualUpdate />} />
      <Route path="delete" element={<IndividualDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndividualRoutes;
