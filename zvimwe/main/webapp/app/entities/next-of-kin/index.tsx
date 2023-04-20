import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NextOfKin from './next-of-kin';
import NextOfKinDetail from './next-of-kin-detail';
import NextOfKinUpdate from './next-of-kin-update';
import NextOfKinDeleteDialog from './next-of-kin-delete-dialog';

const NextOfKinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NextOfKin />} />
    <Route path="new" element={<NextOfKinUpdate />} />
    <Route path=":id">
      <Route index element={<NextOfKinDetail />} />
      <Route path="edit" element={<NextOfKinUpdate />} />
      <Route path="delete" element={<NextOfKinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NextOfKinRoutes;
