import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './invoice-line.reducer';

export const InvoiceLineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const invoiceLineEntity = useAppSelector(state => state.invoiceLine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceLineDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.invoiceLine.detail.title">InvoiceLine</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.id}</dd>
          <dt>
            <span id="paymentReason">
              <Translate contentKey="medicalAidSystemApp.invoiceLine.paymentReason">Payment Reason</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.paymentReason}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="medicalAidSystemApp.invoiceLine.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.amount}</dd>
        </dl>
        <Button tag={Link} to="/invoice-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice-line/${invoiceLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceLineDetail;
