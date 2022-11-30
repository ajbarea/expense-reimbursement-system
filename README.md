# Expense Reimbursement System [ERS]
> The Expense Reimbursement System allows an Employee to submit reimbursement requests to their employer to be refunded for various paid activities. Financial Managers can approve or deny pending reimbursement tickets. All users and their submitted requests are stored in a PostgreSQL database.

---
### (Problem Statement): Can an expense reimbursement system be implemented to allow for the submission, approval, and the denial of employee expense reimbursements?

#### 1) As an Employee I want an expense reimbursement system so that I can submit reimbursement requests and view pending request tickets.
Acceptance criteria:
  * a) Employees must be able to log into the ERS application using username and password input
  * b) New users of the ERS system must not be allowed to register with a username that is already registered
  * c) New users created in the ERS should default to a non-administrative role.
  * d) Requests must include the financial cost and a description of the expense.
  * e) Employees must be able to view previously submitted reimbursement submissions.

#### 2) As a Financial Manager I want an expense reimbursement system to process employee reimbursement requests.
Acceptance criteria:
  * a) Submitted tickets are initially 'Pending' and may be either 'Approved' or 'Denied'.
  * b) Reimbursement expense requests cannot change status after processing.
---

### Project Specifications

**State-chart Diagram** 
<br>
![](./assets/state.png)
<br>

**Logical Model**
<br>
![](./assets/logical.png)
<br>

**Physical Model**
<br>
![](./assets/physical.png)
<br>

**Activity Diagram**
<br>
![](./assets/activity.png)
<br>

## Tech Stack
- [ ] Java 8
- [ ] Apache Maven
- [ ] PostgreSQL
- [ ] Javalin
- [ ] Git SCM
- [ ] Postman
