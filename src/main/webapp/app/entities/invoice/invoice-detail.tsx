import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './invoice.reducer';

export const InvoiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.invoice.detail.title">Invoice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.id}</dd>
          <dt>
            <span id="invoiceNumber">
              <Translate contentKey="medicalAidSystemApp.invoice.invoiceNumber">Invoice Number</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceNumber}</dd>
          <dt>
            <span id="invoiceStatus">
              <Translate contentKey="medicalAidSystemApp.invoice.invoiceStatus">Invoice Status</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceStatus}</dd>
          <dt>
            <span id="amountPayable">
              <Translate contentKey="medicalAidSystemApp.invoice.amountPayable">Amount Payable</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.amountPayable}</dd>
          <dt>
            <span id="invoiceDate">
              <Translate contentKey="medicalAidSystemApp.invoice.invoiceDate">Invoice Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.invoiceDate ? <TextFormat value={invoiceEntity.invoiceDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="nextInvoiceDate">
              <Translate contentKey="medicalAidSystemApp.invoice.nextInvoiceDate">Next Invoice Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.nextInvoiceDate ? (
              <TextFormat value={invoiceEntity.nextInvoiceDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="invoiceAmount">
              <Translate contentKey="medicalAidSystemApp.invoice.invoiceAmount">Invoice Amount</Translate>
            </span>
          </dt>
          <dd>{invoiceEntity.invoiceAmount}</dd>
          <dt>
            <span id="expectedPaymentDate">
              <Translate contentKey="medicalAidSystemApp.invoice.expectedPaymentDate">Expected Payment Date</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.expectedPaymentDate ? (
              <TextFormat value={invoiceEntity.expectedPaymentDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gracePeriod">
              <Translate contentKey="medicalAidSystemApp.invoice.gracePeriod">Grace Period</Translate>
            </span>
          </dt>
          <dd>
            {invoiceEntity.gracePeriod ? <TextFormat value={invoiceEntity.gracePeriod} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.invoice.policy">Policy</Translate>
          </dt>
          <dd>{invoiceEntity.policy ? invoiceEntity.policy.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.invoice.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{invoiceEntity.contactDetails ? invoiceEntity.contactDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceDetail;
