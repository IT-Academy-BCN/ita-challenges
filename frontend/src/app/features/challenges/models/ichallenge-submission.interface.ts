export interface IChallengeSubmission {
  challengeId: string,
  userId: string,
	code: string,
	revealOfficialSolution?: boolean,
}
