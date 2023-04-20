import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TarrifClaim from './tarrif-claim';
import TarrifClaimDetail from './tarrif-claim-detail';
import TarrifClaimUpdate from './tarrif-claim-update';
import TarrifClaimDeleteDialog from './tarrif-claim-delete-dialog';

const TarrifClaimRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TarrifClaim />} />
    <Route path="new" element={<TarrifClaimUpdate />} />
    <Route path=":id">
      <Route index element={<TarrifClaimDetail />} />
      <Route path="edit" element={<TarrifClaimUpdate />} />
      <Route path="delete" element={<TarrifClaimDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TarrifClaimRoutes;
