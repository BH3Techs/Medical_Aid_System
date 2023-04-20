import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banking-details.reducer';

export const BankingDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankingDetailsEntity = useAppSelector(state => state.bankingDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankingDetailsDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.bankingDetails.detail.title">BankingDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankingDetailsEntity.id}</dd>
          <dt>
            <span id="accountName">
              <Translate contentKey="medicalAidSystemApp.bankingDetails.accountName">Account Name</Translate>
            </span>
          </dt>
          <dd>{bankingDetailsEntity.accountName}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="medicalAidSystemApp.bankingDetails.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{bankingDetailsEntity.accountNumber}</dd>
          <dt>
            <span id="swiftCode">
              <Translate contentKey="medicalAidSystemApp.bankingDetails.swiftCode">Swift Code</Translate>
            </span>
          </dt>
          <dd>{bankingDetailsEntity.swiftCode}</dd>
          <dt>
            <span id="bankName">
              <Translate contentKey="medicalAidSystemApp.bankingDetails.bankName">Bank Name</Translate>
            </span>
          </dt>
          <dd>{bankingDetailsEntity.bankName}</dd>
        </dl>
        <Button tag={Link} to="/banking-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banking-details/${bankingDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankingDetailsDetail;
